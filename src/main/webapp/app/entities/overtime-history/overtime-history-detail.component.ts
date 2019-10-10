import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOvertimeHistory } from 'app/shared/model/overtime-history.model';

@Component({
  selector: 'jhi-overtime-history-detail',
  templateUrl: './overtime-history-detail.component.html'
})
export class OvertimeHistoryDetailComponent implements OnInit {
  overtimeHistory: IOvertimeHistory;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ overtimeHistory }) => {
      this.overtimeHistory = overtimeHistory;
    });
  }

  previousState() {
    window.history.back();
  }
}
