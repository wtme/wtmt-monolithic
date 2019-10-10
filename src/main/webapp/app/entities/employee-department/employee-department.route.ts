import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { EmployeeDepartment } from 'app/shared/model/employee-department.model';
import { EmployeeDepartmentService } from './employee-department.service';
import { EmployeeDepartmentComponent } from './employee-department.component';
import { EmployeeDepartmentDetailComponent } from './employee-department-detail.component';
import { EmployeeDepartmentUpdateComponent } from './employee-department-update.component';
import { EmployeeDepartmentDeletePopupComponent } from './employee-department-delete-dialog.component';
import { IEmployeeDepartment } from 'app/shared/model/employee-department.model';

@Injectable({ providedIn: 'root' })
export class EmployeeDepartmentResolve implements Resolve<IEmployeeDepartment> {
  constructor(private service: EmployeeDepartmentService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEmployeeDepartment> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<EmployeeDepartment>) => response.ok),
        map((employeeDepartment: HttpResponse<EmployeeDepartment>) => employeeDepartment.body)
      );
    }
    return of(new EmployeeDepartment());
  }
}

export const employeeDepartmentRoute: Routes = [
  {
    path: '',
    component: EmployeeDepartmentComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'wmteApp.employeeDepartment.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EmployeeDepartmentDetailComponent,
    resolve: {
      employeeDepartment: EmployeeDepartmentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'wmteApp.employeeDepartment.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EmployeeDepartmentUpdateComponent,
    resolve: {
      employeeDepartment: EmployeeDepartmentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'wmteApp.employeeDepartment.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EmployeeDepartmentUpdateComponent,
    resolve: {
      employeeDepartment: EmployeeDepartmentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'wmteApp.employeeDepartment.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const employeeDepartmentPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: EmployeeDepartmentDeletePopupComponent,
    resolve: {
      employeeDepartment: EmployeeDepartmentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'wmteApp.employeeDepartment.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
