import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Overtime } from 'app/shared/model/overtime.model';
import { OvertimeService } from './overtime.service';
import { OvertimeComponent } from './overtime.component';
import { OvertimeDetailComponent } from './overtime-detail.component';
import { OvertimeUpdateComponent } from './overtime-update.component';
import { OvertimeDeletePopupComponent } from './overtime-delete-dialog.component';
import { IOvertime } from 'app/shared/model/overtime.model';

@Injectable({ providedIn: 'root' })
export class OvertimeResolve implements Resolve<IOvertime> {
  constructor(private service: OvertimeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IOvertime> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Overtime>) => response.ok),
        map((overtime: HttpResponse<Overtime>) => overtime.body)
      );
    }
    return of(new Overtime());
  }
}

export const overtimeRoute: Routes = [
  {
    path: '',
    component: OvertimeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'wmteApp.overtime.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: OvertimeDetailComponent,
    resolve: {
      overtime: OvertimeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'wmteApp.overtime.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: OvertimeUpdateComponent,
    resolve: {
      overtime: OvertimeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'wmteApp.overtime.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: OvertimeUpdateComponent,
    resolve: {
      overtime: OvertimeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'wmteApp.overtime.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const overtimePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: OvertimeDeletePopupComponent,
    resolve: {
      overtime: OvertimeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'wmteApp.overtime.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
