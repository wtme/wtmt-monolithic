import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { OvertimeComment } from 'app/shared/model/overtime-comment.model';
import { OvertimeCommentService } from './overtime-comment.service';
import { OvertimeCommentComponent } from './overtime-comment.component';
import { OvertimeCommentDetailComponent } from './overtime-comment-detail.component';
import { OvertimeCommentUpdateComponent } from './overtime-comment-update.component';
import { OvertimeCommentDeletePopupComponent } from './overtime-comment-delete-dialog.component';
import { IOvertimeComment } from 'app/shared/model/overtime-comment.model';

@Injectable({ providedIn: 'root' })
export class OvertimeCommentResolve implements Resolve<IOvertimeComment> {
  constructor(private service: OvertimeCommentService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IOvertimeComment> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<OvertimeComment>) => response.ok),
        map((overtimeComment: HttpResponse<OvertimeComment>) => overtimeComment.body)
      );
    }
    return of(new OvertimeComment());
  }
}

export const overtimeCommentRoute: Routes = [
  {
    path: '',
    component: OvertimeCommentComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'wmteApp.overtimeComment.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: OvertimeCommentDetailComponent,
    resolve: {
      overtimeComment: OvertimeCommentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'wmteApp.overtimeComment.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: OvertimeCommentUpdateComponent,
    resolve: {
      overtimeComment: OvertimeCommentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'wmteApp.overtimeComment.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: OvertimeCommentUpdateComponent,
    resolve: {
      overtimeComment: OvertimeCommentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'wmteApp.overtimeComment.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const overtimeCommentPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: OvertimeCommentDeletePopupComponent,
    resolve: {
      overtimeComment: OvertimeCommentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'wmteApp.overtimeComment.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
