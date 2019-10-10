import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DepartmentRole } from 'app/shared/model/department-role.model';
import { DepartmentRoleService } from './department-role.service';
import { DepartmentRoleComponent } from './department-role.component';
import { DepartmentRoleDetailComponent } from './department-role-detail.component';
import { DepartmentRoleUpdateComponent } from './department-role-update.component';
import { DepartmentRoleDeletePopupComponent } from './department-role-delete-dialog.component';
import { IDepartmentRole } from 'app/shared/model/department-role.model';

@Injectable({ providedIn: 'root' })
export class DepartmentRoleResolve implements Resolve<IDepartmentRole> {
  constructor(private service: DepartmentRoleService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDepartmentRole> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<DepartmentRole>) => response.ok),
        map((departmentRole: HttpResponse<DepartmentRole>) => departmentRole.body)
      );
    }
    return of(new DepartmentRole());
  }
}

export const departmentRoleRoute: Routes = [
  {
    path: '',
    component: DepartmentRoleComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'wmteApp.departmentRole.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DepartmentRoleDetailComponent,
    resolve: {
      departmentRole: DepartmentRoleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'wmteApp.departmentRole.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DepartmentRoleUpdateComponent,
    resolve: {
      departmentRole: DepartmentRoleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'wmteApp.departmentRole.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DepartmentRoleUpdateComponent,
    resolve: {
      departmentRole: DepartmentRoleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'wmteApp.departmentRole.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const departmentRolePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DepartmentRoleDeletePopupComponent,
    resolve: {
      departmentRole: DepartmentRoleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'wmteApp.departmentRole.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
