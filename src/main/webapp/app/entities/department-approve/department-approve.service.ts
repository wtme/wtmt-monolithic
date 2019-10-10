import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDepartmentApprove } from 'app/shared/model/department-approve.model';

type EntityResponseType = HttpResponse<IDepartmentApprove>;
type EntityArrayResponseType = HttpResponse<IDepartmentApprove[]>;

@Injectable({ providedIn: 'root' })
export class DepartmentApproveService {
  public resourceUrl = SERVER_API_URL + 'api/department-approves';

  constructor(protected http: HttpClient) {}

  create(departmentApprove: IDepartmentApprove): Observable<EntityResponseType> {
    return this.http.post<IDepartmentApprove>(this.resourceUrl, departmentApprove, { observe: 'response' });
  }

  update(departmentApprove: IDepartmentApprove): Observable<EntityResponseType> {
    return this.http.put<IDepartmentApprove>(this.resourceUrl, departmentApprove, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDepartmentApprove>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDepartmentApprove[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
