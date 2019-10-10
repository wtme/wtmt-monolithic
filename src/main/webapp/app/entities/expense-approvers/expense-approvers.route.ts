import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ExpenseApprovers } from 'app/shared/model/expense-approvers.model';
import { ExpenseApproversService } from './expense-approvers.service';
import { ExpenseApproversComponent } from './expense-approvers.component';
import { ExpenseApproversDetailComponent } from './expense-approvers-detail.component';
import { ExpenseApproversUpdateComponent } from './expense-approvers-update.component';
import { ExpenseApproversDeletePopupComponent } from './expense-approvers-delete-dialog.component';
import { IExpenseApprovers } from 'app/shared/model/expense-approvers.model';

@Injectable({ providedIn: 'root' })
export class ExpenseApproversResolve implements Resolve<IExpenseApprovers> {
  constructor(private service: ExpenseApproversService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IExpenseApprovers> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ExpenseApprovers>) => response.ok),
        map((expenseApprovers: HttpResponse<ExpenseApprovers>) => expenseApprovers.body)
      );
    }
    return of(new ExpenseApprovers());
  }
}

export const expenseApproversRoute: Routes = [
  {
    path: '',
    component: ExpenseApproversComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'wmteApp.expenseApprovers.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ExpenseApproversDetailComponent,
    resolve: {
      expenseApprovers: ExpenseApproversResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'wmteApp.expenseApprovers.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ExpenseApproversUpdateComponent,
    resolve: {
      expenseApprovers: ExpenseApproversResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'wmteApp.expenseApprovers.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ExpenseApproversUpdateComponent,
    resolve: {
      expenseApprovers: ExpenseApproversResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'wmteApp.expenseApprovers.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const expenseApproversPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ExpenseApproversDeletePopupComponent,
    resolve: {
      expenseApprovers: ExpenseApproversResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'wmteApp.expenseApprovers.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
