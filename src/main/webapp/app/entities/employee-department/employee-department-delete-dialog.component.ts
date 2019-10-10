import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEmployeeDepartment } from 'app/shared/model/employee-department.model';
import { EmployeeDepartmentService } from './employee-department.service';

@Component({
  selector: 'jhi-employee-department-delete-dialog',
  templateUrl: './employee-department-delete-dialog.component.html'
})
export class EmployeeDepartmentDeleteDialogComponent {
  employeeDepartment: IEmployeeDepartment;

  constructor(
    protected employeeDepartmentService: EmployeeDepartmentService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.employeeDepartmentService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'employeeDepartmentListModification',
        content: 'Deleted an employeeDepartment'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-employee-department-delete-popup',
  template: ''
})
export class EmployeeDepartmentDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ employeeDepartment }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(EmployeeDepartmentDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.employeeDepartment = employeeDepartment;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/employee-department', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/employee-department', { outlets: { popup: null } }]);
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
