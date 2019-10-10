import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IExpenseApprovers } from 'app/shared/model/expense-approvers.model';
import { ExpenseApproversService } from './expense-approvers.service';

@Component({
  selector: 'jhi-expense-approvers-delete-dialog',
  templateUrl: './expense-approvers-delete-dialog.component.html'
})
export class ExpenseApproversDeleteDialogComponent {
  expenseApprovers: IExpenseApprovers;

  constructor(
    protected expenseApproversService: ExpenseApproversService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.expenseApproversService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'expenseApproversListModification',
        content: 'Deleted an expenseApprovers'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-expense-approvers-delete-popup',
  template: ''
})
export class ExpenseApproversDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ expenseApprovers }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ExpenseApproversDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.expenseApprovers = expenseApprovers;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/expense-approvers', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/expense-approvers', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
