import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEmployeeDepartment } from 'app/shared/model/employee-department.model';

type EntityResponseType = HttpResponse<IEmployeeDepartment>;
type EntityArrayResponseType = HttpResponse<IEmployeeDepartment[]>;

@Injectable({ providedIn: 'root' })
export class EmployeeDepartmentService {
  public resourceUrl = SERVER_API_URL + 'api/employee-departments';

  constructor(protected http: HttpClient) {}

  create(employeeDepartment: IEmployeeDepartment): Observable<EntityResponseType> {
    return this.http.post<IEmployeeDepartment>(this.resourceUrl, employeeDepartment, { observe: 'response' });
  }

  update(employeeDepartment: IEmployeeDepartment): Observable<EntityResponseType> {
    return this.http.put<IEmployeeDepartment>(this.resourceUrl, employeeDepartment, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEmployeeDepartment>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEmployeeDepartment[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
