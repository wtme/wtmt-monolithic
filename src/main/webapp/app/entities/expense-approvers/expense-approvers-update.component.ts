import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IExpenseApprovers, ExpenseApprovers } from 'app/shared/model/expense-approvers.model';
import { ExpenseApproversService } from './expense-approvers.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee/employee.service';
import { IDepartment } from 'app/shared/model/department.model';
import { DepartmentService } from 'app/entities/department/department.service';
import { IDepartmentRole } from 'app/shared/model/department-role.model';
import { DepartmentRoleService } from 'app/entities/department-role/department-role.service';

@Component({
  selector: 'jhi-expense-approvers-update',
  templateUrl: './expense-approvers-update.component.html'
})
export class ExpenseApproversUpdateComponent implements OnInit {
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
    protected expenseApproversService: ExpenseApproversService,
    protected employeeService: EmployeeService,
    protected departmentService: DepartmentService,
    protected departmentRoleService: DepartmentRoleService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ expenseApprovers }) => {
      this.updateForm(expenseApprovers);
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

  updateForm(expenseApprovers: IExpenseApprovers) {
    this.editForm.patchValue({
      id: expenseApprovers.id,
      employeeId: expenseApprovers.employeeId,
      departmentId: expenseApprovers.departmentId,
      departmentRoleId: expenseApprovers.departmentRoleId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const expenseApprovers = this.createFromForm();
    if (expenseApprovers.id !== undefined) {
      this.subscribeToSaveResponse(this.expenseApproversService.update(expenseApprovers));
    } else {
      this.subscribeToSaveResponse(this.expenseApproversService.create(expenseApprovers));
    }
  }

  private createFromForm(): IExpenseApprovers {
    return {
      ...new ExpenseApprovers(),
      id: this.editForm.get(['id']).value,
      employeeId: this.editForm.get(['employeeId']).value,
      departmentId: this.editForm.get(['departmentId']).value,
      departmentRoleId: this.editForm.get(['departmentRoleId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IExpenseApprovers>>) {
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
