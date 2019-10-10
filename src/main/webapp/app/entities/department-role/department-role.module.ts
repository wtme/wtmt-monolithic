import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WmteSharedModule } from 'app/shared/shared.module';
import { DepartmentRoleComponent } from './department-role.component';
import { DepartmentRoleDetailComponent } from './department-role-detail.component';
import { DepartmentRoleUpdateComponent } from './department-role-update.component';
import { DepartmentRoleDeletePopupComponent, DepartmentRoleDeleteDialogComponent } from './department-role-delete-dialog.component';
import { departmentRoleRoute, departmentRolePopupRoute } from './department-role.route';

const ENTITY_STATES = [...departmentRoleRoute, ...departmentRolePopupRoute];

@NgModule({
  imports: [WmteSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DepartmentRoleComponent,
    DepartmentRoleDetailComponent,
    DepartmentRoleUpdateComponent,
    DepartmentRoleDeleteDialogComponent,
    DepartmentRoleDeletePopupComponent
  ],
  entryComponents: [DepartmentRoleDeleteDialogComponent]
})
export class WmteDepartmentRoleModule {}
