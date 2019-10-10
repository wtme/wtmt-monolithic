import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDepartmentApprove } from 'app/shared/model/department-approve.model';
import { DepartmentApproveService } from './department-approve.service';

@Component({
  selector: 'jhi-department-approve-delete-dialog',
  templateUrl: './department-approve-delete-dialog.component.html'
})
export class DepartmentApproveDeleteDialogComponent {
  departmentApprove: IDepartmentApprove;

  constructor(
    protected departmentApproveService: DepartmentApproveService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.departmentApproveService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'departmentApproveListModification',
        content: 'Deleted an departmentApprove'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-department-approve-delete-popup',
  template: ''
})
export class DepartmentApproveDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ departmentApprove }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DepartmentApproveDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.departmentApprove = departmentApprove;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/department-approve', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/department-approve', { outlets: { popup: null } }]);
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
