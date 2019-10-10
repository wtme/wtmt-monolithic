import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IDepartmentApprove, DepartmentApprove } from 'app/shared/model/department-approve.model';
import { DepartmentApproveService } from './department-approve.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee/employee.service';
import { IDepartment } from 'app/shared/model/department.model';
import { DepartmentService } from 'app/entities/department/department.service';
import { IDepartmentRole } from 'app/shared/model/department-role.model';
import { DepartmentRoleService } from 'app/entities/department-role/department-role.service';

@Component({
  selector: 'jhi-department-approve-update',
  templateUrl: './department-approve-update.component.html'
})
export class DepartmentApproveUpdateComponent implements OnInit {
  isSaving: boolean;

  employees: IEmployee[];

  departments: IDepartment[];

  departmentroles: IDepartmentRole[];

  editForm = this.fb.group({
    id: [],
    employeeId: [],
    departmentId: [],
    departmentRoleId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected departmentApproveService: DepartmentApproveService,
    protected employeeService: EmployeeService,
    protected departmentService: DepartmentService,
    protected departmentRoleService: DepartmentRoleService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ departmentApprove }) => {
      this.updateForm(departmentApprove);
    });
    this.employeeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEmployee[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEmployee[]>) => response.body)
      )
      .subscribe((res: IEmployee[]) => (this.employees = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.departmentService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IDepartment[]>) => mayBeOk.ok),
        map((response: HttpResponse<IDepartment[]>) => response.body)
      )
      .subscribe((res: IDepartment[]) => (this.departments = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.departmentRoleService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IDepartmentRole[]>) => mayBeOk.ok),
        map((response: HttpResponse<IDepartmentRole[]>) => response.body)
      )
      .subscribe((res: IDepartmentRole[]) => (this.departmentroles = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(departmentApprove: IDepartmentApprove) {
    this.editForm.patchValue({
      id: departmentApprove.id,
      employeeId: departmentApprove.employeeId,
      departmentId: departmentApprove.departmentId,
      departmentRoleId: departmentApprove.departmentRoleId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const departmentApprove = this.createFromForm();
    if (departmentApprove.id !== undefined) {
      this.subscribeToSaveResponse(this.departmentApproveService.update(departmentApprove));
    } else {
      this.subscribeToSaveResponse(this.departmentApproveService.create(departmentApprove));
    }
  }

  private createFromForm(): IDepartmentApprove {
    return {
      ...new DepartmentApprove(),
      id: this.editForm.get(['id']).value,
      employeeId: this.editForm.get(['employeeId']).value,
      departmentId: this.editForm.get(['departmentId']).value,
      departmentRoleId: this.editForm.get(['departmentRoleId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDepartmentApprove>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackEmployeeById(index: number, item: IEmployee) {
    return item.id;
  }

  trackDepartmentById(index: number, item: IDepartment) {
    return item.id;
  }

  trackDepartmentRoleById(index: number, item: IDepartmentRole) {
    return item.id;
  }
}
