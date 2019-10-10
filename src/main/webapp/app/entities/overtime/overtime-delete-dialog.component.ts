import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOvertime } from 'app/shared/model/overtime.model';
import { OvertimeService } from './overtime.service';

@Component({
  selector: 'jhi-overtime-delete-dialog',
  templateUrl: './overtime-delete-dialog.component.html'
})
export class OvertimeDeleteDialogComponent {
  overtime: IOvertime;

  constructor(protected overtimeService: OvertimeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.overtimeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'overtimeListModification',
        content: 'Deleted an overtime'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-overtime-delete-popup',
  template: ''
})
export class OvertimeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ overtime }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(OvertimeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.overtime = overtime;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/overtime', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/overtime', { outlets: { popup: null } }]);
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
