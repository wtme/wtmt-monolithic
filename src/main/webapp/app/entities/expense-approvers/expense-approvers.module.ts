import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WmteSharedModule } from 'app/shared/shared.module';
import { ExpenseApproversComponent } from './expense-approvers.component';
import { ExpenseApproversDetailComponent } from './expense-approvers-detail.component';
import { ExpenseApproversUpdateComponent } from './expense-approvers-update.component';
import { ExpenseApproversDeletePopupComponent, ExpenseApproversDeleteDialogComponent } from './expense-approvers-delete-dialog.component';
import { expenseApproversRoute, expenseApproversPopupRoute } from './expense-approvers.route';

const ENTITY_STATES = [...expenseApproversRoute, ...expenseApproversPopupRoute];

@NgModule({
  imports: [WmteSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ExpenseApproversComponent,
    ExpenseApproversDetailComponent,
    ExpenseApproversUpdateComponent,
    ExpenseApproversDeleteDialogComponent,
    ExpenseApproversDeletePopupComponent
  ],
  entryComponents: [ExpenseApproversDeleteDialogComponent]
})
export class WmteExpenseApproversModule {}
