import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WmteSharedModule } from 'app/shared/shared.module';
import { OvertimeHistoryComponent } from './overtime-history.component';
import { OvertimeHistoryDetailComponent } from './overtime-history-detail.component';
import { OvertimeHistoryUpdateComponent } from './overtime-history-update.component';
import { OvertimeHistoryDeletePopupComponent, OvertimeHistoryDeleteDialogComponent } from './overtime-history-delete-dialog.component';
import { overtimeHistoryRoute, overtimeHistoryPopupRoute } from './overtime-history.route';

const ENTITY_STATES = [...overtimeHistoryRoute, ...overtimeHistoryPopupRoute];

@NgModule({
  imports: [WmteSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    OvertimeHistoryComponent,
    OvertimeHistoryDetailComponent,
    OvertimeHistoryUpdateComponent,
    OvertimeHistoryDeleteDialogComponent,
    OvertimeHistoryDeletePopupComponent
  ],
  entryComponents: [OvertimeHistoryDeleteDialogComponent]
})
export class WmteOvertimeHistoryModule {}
