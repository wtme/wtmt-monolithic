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
import { IOvertimeComment, OvertimeComment } from 'app/shared/model/overtime-comment.model';
import { OvertimeCommentService } from './overtime-comment.service';
import { IOvertime } from 'app/shared/model/overtime.model';
import { OvertimeService } from 'app/entities/overtime/overtime.service';

@Component({
  selector: 'jhi-overtime-comment-update',
  templateUrl: './overtime-comment-update.component.html'
})
export class OvertimeCommentUpdateComponent implements OnInit {
  isSaving: boolean;

  overtimes: IOvertime[];

  editForm = this.fb.group({
    id: [],
    comment: [],
    createdDate: [],
    employeeName: [],
    employeeEmail: [],
    overtimeId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected overtimeCommentService: OvertimeCommentService,
    protected overtimeService: OvertimeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ overtimeComment }) => {
      this.updateForm(overtimeComment);
    });
    this.overtimeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IOvertime[]>) => mayBeOk.ok),
        map((response: HttpResponse<IOvertime[]>) => response.body)
      )
      .subscribe((res: IOvertime[]) => (this.overtimes = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(overtimeComment: IOvertimeComment) {
    this.editForm.patchValue({
      id: overtimeComment.id,
      comment: overtimeComment.comment,
      createdDate: overtimeComment.createdDate != null ? overtimeComment.createdDate.format(DATE_TIME_FORMAT) : null,
      employeeName: overtimeComment.employeeName,
      employeeEmail: overtimeComment.employeeEmail,
      overtimeId: overtimeComment.overtimeId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const overtimeComment = this.createFromForm();
    if (overtimeComment.id !== undefined) {
      this.subscribeToSaveResponse(this.overtimeCommentService.update(overtimeComment));
    } else {
      this.subscribeToSaveResponse(this.overtimeCommentService.create(overtimeComment));
    }
  }

  private createFromForm(): IOvertimeComment {
    return {
      ...new OvertimeComment(),
      id: this.editForm.get(['id']).value,
      comment: this.editForm.get(['comment']).value,
      createdDate:
        this.editForm.get(['createdDate']).value != null ? moment(this.editForm.get(['createdDate']).value, DATE_TIME_FORMAT) : undefined,
      employeeName: this.editForm.get(['employeeName']).value,
      employeeEmail: this.editForm.get(['employeeEmail']).value,
      overtimeId: this.editForm.get(['overtimeId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOvertimeComment>>) {
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
