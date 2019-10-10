import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WmteSharedModule } from 'app/shared/shared.module';
import { OvertimeApprovalComponent } from './overtime-approval.component';
import { OvertimeApprovalDetailComponent } from './overtime-approval-detail.component';
import { OvertimeApprovalUpdateComponent } from './overtime-approval-update.component';
import { OvertimeApprovalDeletePopupComponent, OvertimeApprovalDeleteDialogComponent } from './overtime-approval-delete-dialog.component';
import { overtimeApprovalRoute, overtimeApprovalPopupRoute } from './overtime-approval.route';

const ENTITY_STATES = [...overtimeApprovalRoute, ...overtimeApprovalPopupRoute];

@NgModule({
  imports: [WmteSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    OvertimeApprovalComponent,
    OvertimeApprovalDetailComponent,
    OvertimeApprovalUpdateComponent,
    OvertimeApprovalDeleteDialogComponent,
    OvertimeApprovalDeletePopupComponent
  ],
  entryComponents: [OvertimeApprovalDeleteDialogComponent]
})
export class WmteOvertimeApprovalModule {}
