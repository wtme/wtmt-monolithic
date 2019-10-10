import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IEmployeeDepartment, EmployeeDepartment } from 'app/shared/model/employee-department.model';
import { EmployeeDepartmentService } from './employee-department.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee/employee.service';
import { IDepartment } from 'app/shared/model/department.model';
import { DepartmentService } from 'app/entities/department/department.service';

@Component({
  selector: 'jhi-employee-department-update',
  templateUrl: './employee-department-update.component.html'
})
export class EmployeeDepartmentUpdateComponent implements OnInit {
  isSaving: boolean;

  employees: IEmployee[];

  departments: IDepartment[];

  editForm = this.fb.group({
    id: [],
    employeeId: [],
    departmentId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected employeeDepartmentService: EmployeeDepartmentService,
    protected employeeService: EmployeeService,
    protected departmentService: DepartmentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ employeeDepartment }) => {
      this.updateForm(employeeDepartment);
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
  }

  updateForm(employeeDepartment: IEmployeeDepartment) {
    this.editForm.patchValue({
      id: employeeDepartment.id,
      employeeId: employeeDepartment.employeeId,
      departmentId: employeeDepartment.departmentId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const employeeDepartment = this.createFromForm();
    if (employeeDepartment.id !== undefined) {
      this.subscribeToSaveResponse(this.employeeDepartmentService.update(employeeDepartment));
    } else {
      this.subscribeToSaveResponse(this.employeeDepartmentService.create(employeeDepartment));
    }
  }

  private createFromForm(): IEmployeeDepartment {
    return {
      ...new EmployeeDepartment(),
      id: this.editForm.get(['id']).value,
      employeeId: this.editForm.get(['employeeId']).value,
      departmentId: this.editForm.get(['departmentId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmployeeDepartment>>) {
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
}
