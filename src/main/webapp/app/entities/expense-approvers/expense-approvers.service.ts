import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IExpenseApprovers } from 'app/shared/model/expense-approvers.model';

type EntityResponseType = HttpResponse<IExpenseApprovers>;
type EntityArrayResponseType = HttpResponse<IExpenseApprovers[]>;

@Injectable({ providedIn: 'root' })
export class ExpenseApproversService {
  public resourceUrl = SERVER_API_URL + 'api/expense-approvers';

  constructor(protected http: HttpClient) {}

  create(expenseApprovers: IExpenseApprovers): Observable<EntityResponseType> {
    return this.http.post<IExpenseApprovers>(this.resourceUrl, expenseApprovers, { observe: 'response' });
  }

  update(expenseApprovers: IExpenseApprovers): Observable<EntityResponseType> {
    return this.http.put<IExpenseApprovers>(this.resourceUrl, expenseApprovers, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IExpenseApprovers>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IExpenseApprovers[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
