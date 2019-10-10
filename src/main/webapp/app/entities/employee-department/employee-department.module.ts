import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WmteSharedModule } from 'app/shared/shared.module';
import { EmployeeDepartmentComponent } from './employee-department.component';
import { EmployeeDepartmentDetailComponent } from './employee-department-detail.component';
import { EmployeeDepartmentUpdateComponent } from './employee-department-update.component';
import {
  EmployeeDepartmentDeletePopupComponent,
  EmployeeDepartmentDeleteDialogComponent
} from './employee-department-delete-dialog.component';
import { employeeDepartmentRoute, employeeDepartmentPopupRoute } from './employee-department.route';

const ENTITY_STATES = [...employeeDepartmentRoute, ...employeeDepartmentPopupRoute];

@NgModule({
  imports: [WmteSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    EmployeeDepartmentComponent,
    EmployeeDepartmentDetailComponent,
    EmployeeDepartmentUpdateComponent,
    EmployeeDepartmentDeleteDialogComponent,
    EmployeeDepartmentDeletePopupComponent
  ],
  entryComponents: [EmployeeDepartmentDeleteDialogComponent]
})
export class WmteEmployeeDepartmentModule {}
