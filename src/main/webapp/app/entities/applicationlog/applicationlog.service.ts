import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IApplicationlog } from 'app/shared/model/applicationlog.model';

type EntityResponseType = HttpResponse<IApplicationlog>;
type EntityArrayResponseType = HttpResponse<IApplicationlog[]>;

@Injectable({ providedIn: 'root' })
export class ApplicationlogService {
  public resourceUrl = SERVER_API_URL + 'api/applicationlogs';

  constructor(protected http: HttpClient) {}

  create(applicationlog: IApplicationlog): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(applicationlog);
    return this.http
      .post<IApplicationlog>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(applicationlog: IApplicationlog): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(applicationlog);
    return this.http
      .put<IApplicationlog>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<IApplicationlog>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IApplicationlog[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(applicationlog: IApplicationlog): IApplicationlog {
    const copy: IApplicationlog = Object.assign({}, applicationlog, {
      action_date: applicationlog.action_date != null && applicationlog.action_date.isValid() ? applicationlog.action_date.toJSON() : null
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
      res.body.forEach((applicationlog: IApplicationlog) => {
        applicationlog.action_date = applicationlog.action_date != null ? moment(applicationlog.action_date) : null;
      });
    }
    return res;
  }
}
