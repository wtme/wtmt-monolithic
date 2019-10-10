import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDepartmentRole } from 'app/shared/model/department-role.model';

type EntityResponseType = HttpResponse<IDepartmentRole>;
type EntityArrayResponseType = HttpResponse<IDepartmentRole[]>;

@Injectable({ providedIn: 'root' })
export class DepartmentRoleService {
  public resourceUrl = SERVER_API_URL + 'api/department-roles';

  constructor(protected http: HttpClient) {}

  create(departmentRole: IDepartmentRole): Observable<EntityResponseType> {
    return this.http.post<IDepartmentRole>(this.resourceUrl, departmentRole, { observe: 'response' });
  }

  update(departmentRole: IDepartmentRole): Observable<EntityResponseType> {
    return this.http.put<IDepartmentRole>(this.resourceUrl, departmentRole, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDepartmentRole>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDepartmentRole[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
