import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDepartment } from 'app/shared/model/department.model';

type EntityResponseType = HttpResponse<IDepartment>;
type EntityArrayResponseType = HttpResponse<IDepartment[]>;

@Injectable({ providedIn: 'root' })
export class DepartmentService {
  public resourceUrl = SERVER_API_URL + 'api/departments';

  constructor(protected http: HttpClient) {}

  create(department: IDepartment): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(department);
    return this.http
      .post<IDepartment>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(department: IDepartment): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(department);
    return this.http
      .put<IDepartment>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDepartment>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDepartment[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(department: IDepartment): IDepartment {
    const copy: IDepartment = Object.assign({}, department, {
      startDate: department.startDate != null && department.startDate.isValid() ? department.startDate.toJSON() : null,
      endDate: department.endDate != null && department.endDate.isValid() ? department.endDate.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.startDate = res.body.startDate != null ? moment(res.body.startDate) : null;
      res.body.endDate = res.body.endDate != null ? moment(res.body.endDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((department: IDepartment) => {
        department.startDate = department.startDate != null ? moment(department.startDate) : null;
        department.endDate = department.endDate != null ? moment(department.endDate) : null;
      });
    }
    return res;
  }
}
