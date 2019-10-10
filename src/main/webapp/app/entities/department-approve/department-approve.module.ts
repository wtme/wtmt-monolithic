import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WmteSharedModule } from 'app/shared/shared.module';
import { DepartmentApproveComponent } from './department-approve.component';
import { DepartmentApproveDetailComponent } from './department-approve-detail.component';
import { DepartmentApproveUpdateComponent } from './department-approve-update.component';
import {
  DepartmentApproveDeletePopupComponent,
  DepartmentApproveDeleteDialogComponent
} from './department-approve-delete-dialog.component';
import { departmentApproveRoute, departmentApprovePopupRoute } from './department-approve.route';

const ENTITY_STATES = [...departmentApproveRoute, ...departmentApprovePopupRoute];

@NgModule({
  imports: [WmteSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DepartmentApproveComponent,
    DepartmentApproveDetailComponent,
    DepartmentApproveUpdateComponent,
    DepartmentApproveDeleteDialogComponent,
    DepartmentApproveDeletePopupComponent
  ],
  entryComponents: [DepartmentApproveDeleteDialogComponent]
})
export class WmteDepartmentApproveModule {}
