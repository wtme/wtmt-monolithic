import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEmployeeHasRole } from 'app/shared/model/employee-has-role.model';

type EntityResponseType = HttpResponse<IEmployeeHasRole>;
type EntityArrayResponseType = HttpResponse<IEmployeeHasRole[]>;

@Injectable({ providedIn: 'root' })
export class EmployeeHasRoleService {
  public resourceUrl = SERVER_API_URL + 'api/employee-has-roles';

  constructor(protected http: HttpClient) {}

  create(employeeHasRole: IEmployeeHasRole): Observable<EntityResponseType> {
    return this.http.post<IEmployeeHasRole>(this.resourceUrl, employeeHasRole, { observe: 'response' });
  }

  update(employeeHasRole: IEmployeeHasRole): Observable<EntityResponseType> {
    return this.http.put<IEmployeeHasRole>(this.resourceUrl, employeeHasRole, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEmployeeHasRole>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEmployeeHasRole[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
