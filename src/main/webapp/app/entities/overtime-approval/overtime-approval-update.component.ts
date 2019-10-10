import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IOvertimeApproval, OvertimeApproval } from 'app/shared/model/overtime-approval.model';
import { OvertimeApprovalService } from './overtime-approval.service';
import { IOvertime } from 'app/shared/model/overtime.model';
import { OvertimeService } from 'app/entities/overtime/overtime.service';

@Component({
  selector: 'jhi-overtime-approval-update',
  templateUrl: './overtime-approval-update.component.html'
})
export class OvertimeApprovalUpdateComponent implements OnInit {
  isSaving: boolean;

  overtimes: IOvertime[];

  editForm = this.fb.group({
    id: [],
    approverName: [],
    approverEmail: [],
    overtimeId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected overtimeApprovalService: OvertimeApprovalService,
    protected overtimeService: OvertimeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ overtimeApproval }) => {
      this.updateForm(overtimeApproval);
    });
    this.overtimeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IOvertime[]>) => mayBeOk.ok),
        map((response: HttpResponse<IOvertime[]>) => response.body)
      )
      .subscribe((res: IOvertime[]) => (this.overtimes = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(overtimeApproval: IOvertimeApproval) {
    this.editForm.patchValue({
      id: overtimeApproval.id,
      approverName: overtimeApproval.approverName,
      approverEmail: overtimeApproval.approverEmail,
      overtimeId: overtimeApproval.overtimeId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const overtimeApproval = this.createFromForm();
    if (overtimeApproval.id !== undefined) {
      this.subscribeToSaveResponse(this.overtimeApprovalService.update(overtimeApproval));
    } else {
      this.subscribeToSaveResponse(this.overtimeApprovalService.create(overtimeApproval));
    }
  }

  private createFromForm(): IOvertimeApproval {
    return {
      ...new OvertimeApproval(),
      id: this.editForm.get(['id']).value,
      approverName: this.editForm.get(['approverName']).value,
      approverEmail: this.editForm.get(['approverEmail']).value,
      overtimeId: this.editForm.get(['overtimeId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOvertimeApproval>>) {
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

  trackOvertimeById(index: number, item: IOvertime) {
    return item.id;
  }
}
