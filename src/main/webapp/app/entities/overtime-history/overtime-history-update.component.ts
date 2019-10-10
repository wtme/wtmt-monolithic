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
import { IOvertimeHistory, OvertimeHistory } from 'app/shared/model/overtime-history.model';
import { OvertimeHistoryService } from './overtime-history.service';
import { IOvertime } from 'app/shared/model/overtime.model';
import { OvertimeService } from 'app/entities/overtime/overtime.service';

@Component({
  selector: 'jhi-overtime-history-update',
  templateUrl: './overtime-history-update.component.html'
})
export class OvertimeHistoryUpdateComponent implements OnInit {
  isSaving: boolean;

  overtimes: IOvertime[];

  editForm = this.fb.group({
    id: [],
    date: [],
    status: [],
    changeType: [],
    who: [],
    overtimeId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected overtimeHistoryService: OvertimeHistoryService,
    protected overtimeService: OvertimeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ overtimeHistory }) => {
      this.updateForm(overtimeHistory);
    });
    this.overtimeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IOvertime[]>) => mayBeOk.ok),
        map((response: HttpResponse<IOvertime[]>) => response.body)
      )
      .subscribe((res: IOvertime[]) => (this.overtimes = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(overtimeHistory: IOvertimeHistory) {
    this.editForm.patchValue({
      id: overtimeHistory.id,
      date: overtimeHistory.date != null ? overtimeHistory.date.format(DATE_TIME_FORMAT) : null,
      status: overtimeHistory.status,
      changeType: overtimeHistory.changeType,
      who: overtimeHistory.who,
      overtimeId: overtimeHistory.overtimeId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const overtimeHistory = this.createFromForm();
    if (overtimeHistory.id !== undefined) {
      this.subscribeToSaveResponse(this.overtimeHistoryService.update(overtimeHistory));
    } else {
      this.subscribeToSaveResponse(this.overtimeHistoryService.create(overtimeHistory));
    }
  }

  private createFromForm(): IOvertimeHistory {
    return {
      ...new OvertimeHistory(),
      id: this.editForm.get(['id']).value,
      date: this.editForm.get(['date']).value != null ? moment(this.editForm.get(['date']).value, DATE_TIME_FORMAT) : undefined,
      status: this.editForm.get(['status']).value,
      changeType: this.editForm.get(['changeType']).value,
      who: this.editForm.get(['who']).value,
      overtimeId: this.editForm.get(['overtimeId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOvertimeHistory>>) {
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
