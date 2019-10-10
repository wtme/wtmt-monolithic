import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { OvertimeApproval } from 'app/shared/model/overtime-approval.model';
import { OvertimeApprovalService } from './overtime-approval.service';
import { OvertimeApprovalComponent } from './overtime-approval.component';
import { OvertimeApprovalDetailComponent } from './overtime-approval-detail.component';
import { OvertimeApprovalUpdateComponent } from './overtime-approval-update.component';
import { OvertimeApprovalDeletePopupComponent } from './overtime-approval-delete-dialog.component';
import { IOvertimeApproval } from 'app/shared/model/overtime-approval.model';

@Injectable({ providedIn: 'root' })
export class OvertimeApprovalResolve implements Resolve<IOvertimeApproval> {
  constructor(private service: OvertimeApprovalService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IOvertimeApproval> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<OvertimeApproval>) => response.ok),
        map((overtimeApproval: HttpResponse<OvertimeApproval>) => overtimeApproval.body)
      );
    }
    return of(new OvertimeApproval());
  }
}

export const overtimeApprovalRoute: Routes = [
  {
    path: '',
    component: OvertimeApprovalComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'wmteApp.overtimeApproval.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: OvertimeApprovalDetailComponent,
    resolve: {
      overtimeApproval: OvertimeApprovalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'wmteApp.overtimeApproval.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: OvertimeApprovalUpdateComponent,
    resolve: {
      overtimeApproval: OvertimeApprovalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'wmteApp.overtimeApproval.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: OvertimeApprovalUpdateComponent,
    resolve: {
      overtimeApproval: OvertimeApprovalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'wmteApp.overtimeApproval.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const overtimeApprovalPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: OvertimeApprovalDeletePopupComponent,
    resolve: {
      overtimeApproval: OvertimeApprovalResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'wmteApp.overtimeApproval.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
