import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IOvertime, Overtime } from 'app/shared/model/overtime.model';
import { OvertimeService } from './overtime.service';

@Component({
  selector: 'jhi-overtime-update',
  templateUrl: './overtime-update.component.html'
})
export class OvertimeUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required, Validators.maxLength(50)]],
    name: [],
    status: [null, [Validators.required]],
    details: [],
    fromDate: [],
    toDate: [],
    createdDate: [],
    overtimeInHours: [],
    totalBillableHours: [],
    totalCostingAmount: [],
    note: [],
    location: [],
    employeeName: [],
    employeeEmail: [],
    departmentName: [],
    departmentCode: []
  });

  constructor(protected overtimeService: OvertimeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ overtime }) => {
      this.updateForm(overtime);
    });
  }

  updateForm(overtime: IOvertime) {
    this.editForm.patchValue({
      id: overtime.id,
      code: overtime.code,
      name: overtime.name,
      status: overtime.status,
      details: overtime.details,
      fromDate: overtime.fromDate != null ? overtime.fromDate.format(DATE_TIME_FORMAT) : null,
      toDate: overtime.toDate != null ? overtime.toDate.format(DATE_TIME_FORMAT) : null,
      createdDate: overtime.createdDate != null ? overtime.createdDate.format(DATE_TIME_FORMAT) : null,
      overtimeInHours: overtime.overtimeInHours,
      totalBillableHours: overtime.totalBillableHours,
      totalCostingAmount: overtime.totalCostingAmount,
      note: overtime.note,
      location: overtime.location,
      employeeName: overtime.employeeName,
      employeeEmail: overtime.employeeEmail,
      departmentName: overtime.departmentName,
      departmentCode: overtime.departmentCode
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const overtime = this.createFromForm();
    if (overtime.id !== undefined) {
      this.subscribeToSaveResponse(this.overtimeService.update(overtime));
    } else {
      this.subscribeToSaveResponse(this.overtimeService.create(overtime));
    }
  }

  private createFromForm(): IOvertime {
    return {
      ...new Overtime(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      name: this.editForm.get(['name']).value,
      status: this.editForm.get(['status']).value,
      details: this.editForm.get(['details']).value,
      fromDate: this.editForm.get(['fromDate']).value != null ? moment(this.editForm.get(['fromDate']).value, DATE_TIME_FORMAT) : undefined,
      toDate: this.editForm.get(['toDate']).value != null ? moment(this.editForm.get(['toDate']).value, DATE_TIME_FORMAT) : undefined,
      createdDate:
        this.editForm.get(['createdDate']).value != null ? moment(this.editForm.get(['createdDate']).value, DATE_TIME_FORMAT) : undefined,
      overtimeInHours: this.editForm.get(['overtimeInHours']).value,
      totalBillableHours: this.editForm.get(['totalBillableHours']).value,
      totalCostingAmount: this.editForm.get(['totalCostingAmount']).value,
      note: this.editForm.get(['note']).value,
      location: this.editForm.get(['location']).value,
      employeeName: this.editForm.get(['employeeName']).value,
      employeeEmail: this.editForm.get(['employeeEmail']).value,
      departmentName: this.editForm.get(['departmentName']).value,
      departmentCode: this.editForm.get(['departmentCode']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOvertime>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
