import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEmployeeHasRole } from 'app/shared/model/employee-has-role.model';
import { EmployeeHasRoleService } from './employee-has-role.service';

@Component({
  selector: 'jhi-employee-has-role-delete-dialog',
  templateUrl: './employee-has-role-delete-dialog.component.html'
})
export class EmployeeHasRoleDeleteDialogComponent {
  employeeHasRole: IEmployeeHasRole;

  constructor(
    protected employeeHasRoleService: EmployeeHasRoleService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.employeeHasRoleService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'employeeHasRoleListModification',
        content: 'Deleted an employeeHasRole'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-employee-has-role-delete-popup',
  template: ''
})
export class EmployeeHasRoleDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ employeeHasRole }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(EmployeeHasRoleDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.employeeHasRole = employeeHasRole;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/employee-has-role', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/employee-has-role', { outlets: { popup: null } }]);
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
