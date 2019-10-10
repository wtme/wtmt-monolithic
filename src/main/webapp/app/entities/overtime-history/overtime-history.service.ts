import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IOvertimeHistory } from 'app/shared/model/overtime-history.model';

type EntityResponseType = HttpResponse<IOvertimeHistory>;
type EntityArrayResponseType = HttpResponse<IOvertimeHistory[]>;

@Injectable({ providedIn: 'root' })
export class OvertimeHistoryService {
  public resourceUrl = SERVER_API_URL + 'api/overtime-histories';

  constructor(protected http: HttpClient) {}

  create(overtimeHistory: IOvertimeHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(overtimeHistory);
    return this.http
      .post<IOvertimeHistory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(overtimeHistory: IOvertimeHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(overtimeHistory);
    return this.http
      .put<IOvertimeHistory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IOvertimeHistory>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IOvertimeHistory[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(overtimeHistory: IOvertimeHistory): IOvertimeHistory {
    const copy: IOvertimeHistory = Object.assign({}, overtimeHistory, {
      date: overtimeHistory.date != null && overtimeHistory.date.isValid() ? overtimeHistory.date.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date != null ? moment(res.body.date) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((overtimeHistory: IOvertimeHistory) => {
        overtimeHistory.date = overtimeHistory.date != null ? moment(overtimeHistory.date) : null;
      });
    }
    return res;
  }
}
