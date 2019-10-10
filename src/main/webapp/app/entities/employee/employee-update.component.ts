import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IEmployee, Employee } from 'app/shared/model/employee.model';
import { EmployeeService } from './employee.service';

@Component({
  selector: 'jhi-employee-update',
  templateUrl: './employee-update.component.html'
})
export class EmployeeUpdateComponent implements OnInit {
  isSaving: boolean;

  employees: IEmployee[];

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required, Validators.maxLength(50)]],
    fullname: [null, [Validators.required, Validators.maxLength(50)]],
    login: [null, [Validators.required, Validators.maxLength(50)]],
    email: [null, [Validators.required, Validators.pattern('^[^@s]+@[^@s]+.[^@s]+$')]],
    personalEmail: [null, [Validators.pattern('^[^@s]+@[^@s]+.[^@s]+$')]],
    phoneNumber: [],
    mobile: [],
    dateOfJoining: [],
    gender: [],
    status: [],
    employeeNumber: [],
    dateOfBirth: [],
    note: [],
    userId: [],
    managerId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected employeeService: EmployeeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ employee }) => {
      this.updateForm(employee);
    });
    this.employeeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEmployee[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEmployee[]>) => response.body)
      )
      .subscribe((res: IEmployee[]) => (this.employees = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(employee: IEmployee) {
    this.editForm.patchValue({
      id: employee.id,
      code: employee.code,
      fullname: employee.fullname,
      login: employee.login,
      email: employee.email,
      personalEmail: employee.personalEmail,
      phoneNumber: employee.phoneNumber,
      mobile: employee.mobile,
      dateOfJoining: employee.dateOfJoining != null ? employee.dateOfJoining.format(DATE_TIME_FORMAT) : null,
      gender: employee.gender,
      status: employee.status,
      employeeNumber: employee.employeeNumber,
      dateOfBirth: employee.dateOfBirth != null ? employee.dateOfBirth.format(DATE_TIME_FORMAT) : null,
      note: employee.note,
      userId: employee.userId,
      managerId: employee.managerId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const employee = this.createFromForm();
    if (employee.id !== undefined) {
      this.subscribeToSaveResponse(this.employeeService.update(employee));
    } else {
      this.subscribeToSaveResponse(this.employeeService.create(employee));
    }
  }

  private createFromForm(): IEmployee {
    return {
      ...new Employee(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      fullname: this.editForm.get(['fullname']).value,
      login: this.editForm.get(['login']).value,
      email: this.editForm.get(['email']).value,
      personalEmail: this.editForm.get(['personalEmail']).value,
      phoneNumber: this.editForm.get(['phoneNumber']).value,
      mobile: this.editForm.get(['mobile']).value,
      dateOfJoining:
        this.editForm.get(['dateOfJoining']).value != null
          ? moment(this.editForm.get(['dateOfJoining']).value, DATE_TIME_FORMAT)
          : undefined,
      gender: this.editForm.get(['gender']).value,
      status: this.editForm.get(['status']).value,
      employeeNumber: this.editForm.get(['employeeNumber']).value,
      dateOfBirth:
        this.editForm.get(['dateOfBirth']).value != null ? moment(this.editForm.get(['dateOfBirth']).value, DATE_TIME_FORMAT) : undefined,
      note: this.editForm.get(['note']).value,
      userId: this.editForm.get(['userId']).value,
      managerId: this.editForm.get(['managerId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmployee>>) {
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
