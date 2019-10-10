import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDepartmentRole } from 'app/shared/model/department-role.model';
import { DepartmentRoleService } from './department-role.service';

@Component({
  selector: 'jhi-department-role-delete-dialog',
  templateUrl: './department-role-delete-dialog.component.html'
})
export class DepartmentRoleDeleteDialogComponent {
  departmentRole: IDepartmentRole;

  constructor(
    protected departmentRoleService: DepartmentRoleService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.departmentRoleService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'departmentRoleListModification',
        content: 'Deleted an departmentRole'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-department-role-delete-popup',
  template: ''
})
export class DepartmentRoleDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ departmentRole }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DepartmentRoleDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.departmentRole = departmentRole;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/department-role', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/department-role', { outlets: { popup: null } }]);
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
