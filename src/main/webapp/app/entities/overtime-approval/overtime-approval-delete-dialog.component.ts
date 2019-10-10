import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOvertimeApproval } from 'app/shared/model/overtime-approval.model';
import { OvertimeApprovalService } from './overtime-approval.service';

@Component({
  selector: 'jhi-overtime-approval-delete-dialog',
  templateUrl: './overtime-approval-delete-dialog.component.html'
})
export class OvertimeApprovalDeleteDialogComponent {
  overtimeApproval: IOvertimeApproval;

  constructor(
    protected overtimeApprovalService: OvertimeApprovalService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.overtimeApprovalService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'overtimeApprovalListModification',
        content: 'Deleted an overtimeApproval'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-overtime-approval-delete-popup',
  template: ''
})
export class OvertimeApprovalDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ overtimeApproval }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(OvertimeApprovalDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.overtimeApproval = overtimeApproval;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/overtime-approval', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/overtime-approval', { outlets: { popup: null } }]);
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
