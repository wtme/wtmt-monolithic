import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { OvertimeHistory } from 'app/shared/model/overtime-history.model';
import { OvertimeHistoryService } from './overtime-history.service';
import { OvertimeHistoryComponent } from './overtime-history.component';
import { OvertimeHistoryDetailComponent } from './overtime-history-detail.component';
import { OvertimeHistoryUpdateComponent } from './overtime-history-update.component';
import { OvertimeHistoryDeletePopupComponent } from './overtime-history-delete-dialog.component';
import { IOvertimeHistory } from 'app/shared/model/overtime-history.model';

@Injectable({ providedIn: 'root' })
export class OvertimeHistoryResolve implements Resolve<IOvertimeHistory> {
  constructor(private service: OvertimeHistoryService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IOvertimeHistory> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<OvertimeHistory>) => response.ok),
        map((overtimeHistory: HttpResponse<OvertimeHistory>) => overtimeHistory.body)
      );
    }
    return of(new OvertimeHistory());
  }
}

export const overtimeHistoryRoute: Routes = [
  {
    path: '',
    component: OvertimeHistoryComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'wmteApp.overtimeHistory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: OvertimeHistoryDetailComponent,
    resolve: {
      overtimeHistory: OvertimeHistoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'wmteApp.overtimeHistory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: OvertimeHistoryUpdateComponent,
    resolve: {
      overtimeHistory: OvertimeHistoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'wmteApp.overtimeHistory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: OvertimeHistoryUpdateComponent,
    resolve: {
      overtimeHistory: OvertimeHistoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'wmteApp.overtimeHistory.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const overtimeHistoryPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: OvertimeHistoryDeletePopupComponent,
    resolve: {
      overtimeHistory: OvertimeHistoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'wmteApp.overtimeHistory.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
