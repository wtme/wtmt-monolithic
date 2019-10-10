import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WmteSharedModule } from 'app/shared/shared.module';
import { OvertimeCommentComponent } from './overtime-comment.component';
import { OvertimeCommentDetailComponent } from './overtime-comment-detail.component';
import { OvertimeCommentUpdateComponent } from './overtime-comment-update.component';
import { OvertimeCommentDeletePopupComponent, OvertimeCommentDeleteDialogComponent } from './overtime-comment-delete-dialog.component';
import { overtimeCommentRoute, overtimeCommentPopupRoute } from './overtime-comment.route';

const ENTITY_STATES = [...overtimeCommentRoute, ...overtimeCommentPopupRoute];

@NgModule({
  imports: [WmteSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    OvertimeCommentComponent,
    OvertimeCommentDetailComponent,
    OvertimeCommentUpdateComponent,
    OvertimeCommentDeleteDialogComponent,
    OvertimeCommentDeletePopupComponent
  ],
  entryComponents: [OvertimeCommentDeleteDialogComponent]
})
export class WmteOvertimeCommentModule {}
