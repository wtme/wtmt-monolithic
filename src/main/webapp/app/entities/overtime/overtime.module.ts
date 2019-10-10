import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WmteSharedModule } from 'app/shared/shared.module';
import { OvertimeComponent } from './overtime.component';
import { OvertimeDetailComponent } from './overtime-detail.component';
import { OvertimeUpdateComponent } from './overtime-update.component';
import { OvertimeDeletePopupComponent, OvertimeDeleteDialogComponent } from './overtime-delete-dialog.component';
import { overtimeRoute, overtimePopupRoute } from './overtime.route';

const ENTITY_STATES = [...overtimeRoute, ...overtimePopupRoute];

@NgModule({
  imports: [WmteSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    OvertimeComponent,
    OvertimeDetailComponent,
    OvertimeUpdateComponent,
    OvertimeDeleteDialogComponent,
    OvertimeDeletePopupComponent
  ],
  entryComponents: [OvertimeDeleteDialogComponent]
})
export class WmteOvertimeModule {}
