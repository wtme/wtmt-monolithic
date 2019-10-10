import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IOvertimeApproval } from 'app/shared/model/overtime-approval.model';

type EntityResponseType = HttpResponse<IOvertimeApproval>;
type EntityArrayResponseType = HttpResponse<IOvertimeApproval[]>;

@Injectable({ providedIn: 'root' })
export class OvertimeApprovalService {
  public resourceUrl = SERVER_API_URL + 'api/overtime-approvals';

  constructor(protected http: HttpClient) {}

  create(overtimeApproval: IOvertimeApproval): Observable<EntityResponseType> {
    return this.http.post<IOvertimeApproval>(this.resourceUrl, overtimeApproval, { observe: 'response' });
  }

  update(overtimeApproval: IOvertimeApproval): Observable<EntityResponseType> {
    return this.http.put<IOvertimeApproval>(this.resourceUrl, overtimeApproval, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOvertimeApproval>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOvertimeApproval[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
