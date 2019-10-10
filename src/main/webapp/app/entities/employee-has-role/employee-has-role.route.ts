import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { EmployeeHasRole } from 'app/shared/model/employee-has-role.model';
import { EmployeeHasRoleService } from './employee-has-role.service';
import { EmployeeHasRoleComponent } from './employee-has-role.component';
import { EmployeeHasRoleDetailComponent } from './employee-has-role-detail.component';
import { EmployeeHasRoleUpdateComponent } from './employee-has-role-update.component';
import { EmployeeHasRoleDeletePopupComponent } from './employee-has-role-delete-dialog.component';
import { IEmployeeHasRole } from 'app/shared/model/employee-has-role.model';

@Injectable({ providedIn: 'root' })
export class EmployeeHasRoleResolve implements Resolve<IEmployeeHasRole> {
  constructor(private service: EmployeeHasRoleService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEmployeeHasRole> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<EmployeeHasRole>) => response.ok),
        map((employeeHasRole: HttpResponse<EmployeeHasRole>) => employeeHasRole.body)
      );
    }
    return of(new EmployeeHasRole());
  }
}

export const employeeHasRoleRoute: Routes = [
  {
    path: '',
    component: EmployeeHasRoleComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'wmteApp.employeeHasRole.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EmployeeHasRoleDetailComponent,
    resolve: {
      employeeHasRole: EmployeeHasRoleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'wmteApp.employeeHasRole.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EmployeeHasRoleUpdateComponent,
    resolve: {
      employeeHasRole: EmployeeHasRoleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'wmteApp.employeeHasRole.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EmployeeHasRoleUpdateComponent,
    resolve: {
      employeeHasRole: EmployeeHasRoleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'wmteApp.employeeHasRole.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const employeeHasRolePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: EmployeeHasRoleDeletePopupComponent,
    resolve: {
      employeeHasRole: EmployeeHasRoleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'wmteApp.employeeHasRole.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
