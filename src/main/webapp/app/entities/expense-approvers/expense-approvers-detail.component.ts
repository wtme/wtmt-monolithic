import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IExpenseApprovers } from 'app/shared/model/expense-approvers.model';

@Component({
  selector: 'jhi-expense-approvers-detail',
  templateUrl: './expense-approvers-detail.component.html'
})
export class ExpenseApproversDetailComponent implements OnInit {
  expenseApprovers: IExpenseApprovers;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ expenseApprovers }) => {
      this.expenseApprovers = expenseApprovers;
    });
  }

  previousState() {
    window.history.back();
  }
}
