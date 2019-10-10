import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOvertime } from 'app/shared/model/overtime.model';

@Component({
  selector: 'jhi-overtime-detail',
  templateUrl: './overtime-detail.component.html'
})
export class OvertimeDetailComponent implements OnInit {
  overtime: IOvertime;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ overtime }) => {
      this.overtime = overtime;
    });
  }

  previousState() {
    window.history.back();
  }
}
