import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOvertimeComment } from 'app/shared/model/overtime-comment.model';
import { OvertimeCommentService } from './overtime-comment.service';

@Component({
  selector: 'jhi-overtime-comment-delete-dialog',
  templateUrl: './overtime-comment-delete-dialog.component.html'
})
export class OvertimeCommentDeleteDialogComponent {
  overtimeComment: IOvertimeComment;

  constructor(
    protected overtimeCommentService: OvertimeCommentService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.overtimeCommentService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'overtimeCommentListModification',
        content: 'Deleted an overtimeComment'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-overtime-comment-delete-popup',
  template: ''
})
export class OvertimeCommentDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ overtimeComment }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(OvertimeCommentDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.overtimeComment = overtimeComment;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/overtime-comment', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/overtime-comment', { outlets: { popup: null } }]);
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
