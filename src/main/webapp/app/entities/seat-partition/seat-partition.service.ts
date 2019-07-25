import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISeatPartition } from 'app/shared/model/seat-partition.model';

type EntityResponseType = HttpResponse<ISeatPartition>;
type EntityArrayResponseType = HttpResponse<ISeatPartition[]>;

@Injectable({ providedIn: 'root' })
export class SeatPartitionService {
  public resourceUrl = SERVER_API_URL + 'api/seat-partitions';

  constructor(protected http: HttpClient) {}

  create(seatPartition: ISeatPartition): Observable<EntityResponseType> {
    return this.http.post<ISeatPartition>(this.resourceUrl, seatPartition, { observe: 'response' });
  }

  update(seatPartition: ISeatPartition): Observable<EntityResponseType> {
    return this.http.put<ISeatPartition>(this.resourceUrl, seatPartition, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<ISeatPartition>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISeatPartition[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
