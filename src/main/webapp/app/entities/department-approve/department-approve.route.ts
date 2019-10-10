import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DepartmentApprove } from 'app/shared/model/department-approve.model';
import { DepartmentApproveService } from './department-approve.service';
import { DepartmentApproveComponent } from './department-approve.component';
import { DepartmentApproveDetailComponent } from './department-approve-detail.component';
import { DepartmentApproveUpdateComponent } from './department-approve-update.component';
import { DepartmentApproveDeletePopupComponent } from './department-approve-delete-dialog.component';
import { IDepartmentApprove } from 'app/shared/model/department-approve.model';

@Injectable({ providedIn: 'root' })
export class DepartmentApproveResolve implements Resolve<IDepartmentApprove> {
  constructor(private service: DepartmentApproveService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDepartmentApprove> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<DepartmentApprove>) => response.ok),
        map((departmentApprove: HttpResponse<DepartmentApprove>) => departmentApprove.body)
      );
    }
    return of(new DepartmentApprove());
  }
}

export const departmentApproveRoute: Routes = [
  {
    path: '',
    component: DepartmentApproveComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'wmteApp.departmentApprove.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DepartmentApproveDetailComponent,
    resolve: {
      departmentApprove: DepartmentApproveResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'wmteApp.departmentApprove.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DepartmentApproveUpdateComponent,
    resolve: {
      departmentApprove: DepartmentApproveResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'wmteApp.departmentApprove.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DepartmentApproveUpdateComponent,
    resolve: {
      departmentApprove: DepartmentApproveResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'wmteApp.departmentApprove.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const departmentApprovePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DepartmentApproveDeletePopupComponent,
    resolve: {
      departmentApprove: DepartmentApproveResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'wmteApp.departmentApprove.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
