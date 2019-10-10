import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WmteSharedModule } from 'app/shared/shared.module';
import { EmployeeHasRoleComponent } from './employee-has-role.component';
import { EmployeeHasRoleDetailComponent } from './employee-has-role-detail.component';
import { EmployeeHasRoleUpdateComponent } from './employee-has-role-update.component';
import { EmployeeHasRoleDeletePopupComponent, EmployeeHasRoleDeleteDialogComponent } from './employee-has-role-delete-dialog.component';
import { employeeHasRoleRoute, employeeHasRolePopupRoute } from './employee-has-role.route';

const ENTITY_STATES = [...employeeHasRoleRoute, ...employeeHasRolePopupRoute];

@NgModule({
  imports: [WmteSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    EmployeeHasRoleComponent,
    EmployeeHasRoleDetailComponent,
    EmployeeHasRoleUpdateComponent,
    EmployeeHasRoleDeleteDialogComponent,
    EmployeeHasRoleDeletePopupComponent
  ],
  entryComponents: [EmployeeHasRoleDeleteDialogComponent]
})
export class WmteEmployeeHasRoleModule {}
