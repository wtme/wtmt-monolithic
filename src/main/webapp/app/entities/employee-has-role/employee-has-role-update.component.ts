import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IEmployeeHasRole, EmployeeHasRole } from 'app/shared/model/employee-has-role.model';
import { EmployeeHasRoleService } from './employee-has-role.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee/employee.service';

@Component({
  selector: 'jhi-employee-has-role-update',
  templateUrl: './employee-has-role-update.component.html'
})
export class EmployeeHasRoleUpdateComponent implements OnInit {
  isSaving: boolean;

  employees: IEmployee[];

  editForm = this.fb.group({
    id: [],
    name: [],
    employeeId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected employeeHasRoleService: EmployeeHasRoleService,
    protected employeeService: EmployeeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ employeeHasRole }) => {
      this.updateForm(employeeHasRole);
    });
    this.employeeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEmployee[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEmployee[]>) => response.body)
      )
      .subscribe((res: IEmployee[]) => (this.employees = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(employeeHasRole: IEmployeeHasRole) {
    this.editForm.patchValue({
      id: employeeHasRole.id,
      name: employeeHasRole.name,
      employeeId: employeeHasRole.employeeId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const employeeHasRole = this.createFromForm();
    if (employeeHasRole.id !== undefined) {
      this.subscribeToSaveResponse(this.employeeHasRoleService.update(employeeHasRole));
    } else {
      this.subscribeToSaveResponse(this.employeeHasRoleService.create(employeeHasRole));
    }
  }

  private createFromForm(): IEmployeeHasRole {
    return {
      ...new EmployeeHasRole(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      employeeId: this.editForm.get(['employeeId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmployeeHasRole>>) {
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
}
