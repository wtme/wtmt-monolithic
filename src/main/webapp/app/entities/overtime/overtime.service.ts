import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IOvertime } from 'app/shared/model/overtime.model';

type EntityResponseType = HttpResponse<IOvertime>;
type EntityArrayResponseType = HttpResponse<IOvertime[]>;

@Injectable({ providedIn: 'root' })
export class OvertimeService {
  public resourceUrl = SERVER_API_URL + 'api/overtimes';

  constructor(protected http: HttpClient) {}

  create(overtime: IOvertime): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(overtime);
    return this.http
      .post<IOvertime>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(overtime: IOvertime): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(overtime);
    return this.http
      .put<IOvertime>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IOvertime>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IOvertime[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(overtime: IOvertime): IOvertime {
    const copy: IOvertime = Object.assign({}, overtime, {
      fromDate: overtime.fromDate != null && overtime.fromDate.isValid() ? overtime.fromDate.toJSON() : null,
      toDate: overtime.toDate != null && overtime.toDate.isValid() ? overtime.toDate.toJSON() : null,
      createdDate: overtime.createdDate != null && overtime.createdDate.isValid() ? overtime.createdDate.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fromDate = res.body.fromDate != null ? moment(res.body.fromDate) : null;
      res.body.toDate = res.body.toDate != null ? moment(res.body.toDate) : null;
      res.body.createdDate = res.body.createdDate != null ? moment(res.body.createdDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((overtime: IOvertime) => {
        overtime.fromDate = overtime.fromDate != null ? moment(overtime.fromDate) : null;
        overtime.toDate = overtime.toDate != null ? moment(overtime.toDate) : null;
        overtime.createdDate = overtime.createdDate != null ? moment(overtime.createdDate) : null;
      });
    }
    return res;
  }
}
