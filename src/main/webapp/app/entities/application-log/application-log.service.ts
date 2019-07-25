import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IApplicationLog } from 'app/shared/model/application-log.model';

type EntityResponseType = HttpResponse<IApplicationLog>;
type EntityArrayResponseType = HttpResponse<IApplicationLog[]>;

@Injectable({ providedIn: 'root' })
export class ApplicationLogService {
  public resourceUrl = SERVER_API_URL + 'api/application-logs';

  constructor(protected http: HttpClient) {}

  create(applicationLog: IApplicationLog): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(applicationLog);
    return this.http
      .post<IApplicationLog>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(applicationLog: IApplicationLog): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(applicationLog);
    return this.http
      .put<IApplicationLog>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<IApplicationLog>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IApplicationLog[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(applicationLog: IApplicationLog): IApplicationLog {
    const copy: IApplicationLog = Object.assign({}, applicationLog, {
      action_date: applicationLog.action_date != null && applicationLog.action_date.isValid() ? applicationLog.action_date.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.action_date = res.body.action_date != null ? moment(res.body.action_date) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((applicationLog: IApplicationLog) => {
        applicationLog.action_date = applicationLog.action_date != null ? moment(applicationLog.action_date) : null;
      });
    }
    return res;
  }
}
