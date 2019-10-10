import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOvertimeHistory } from 'app/shared/model/overtime-history.model';
import { OvertimeHistoryService } from './overtime-history.service';

@Component({
  selector: 'jhi-overtime-history-delete-dialog',
  templateUrl: './overtime-history-delete-dialog.component.html'
})
export class OvertimeHistoryDeleteDialogComponent {
  overtimeHistory: IOvertimeHistory;

  constructor(
    protected overtimeHistoryService: OvertimeHistoryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.overtimeHistoryService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'overtimeHistoryListModification',
        content: 'Deleted an overtimeHistory'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-overtime-history-delete-popup',
  template: ''
})
export class OvertimeHistoryDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ overtimeHistory }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(OvertimeHistoryDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.overtimeHistory = overtimeHistory;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/overtime-history', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/overtime-history', { outlets: { popup: null } }]);
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
