import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOvertimeComment } from 'app/shared/model/overtime-comment.model';

@Component({
  selector: 'jhi-overtime-comment-detail',
  templateUrl: './overtime-comment-detail.component.html'
})
export class OvertimeCommentDetailComponent implements OnInit {
  overtimeComment: IOvertimeComment;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ overtimeComment }) => {
      this.overtimeComment = overtimeComment;
    });
  }

  previousState() {
    window.history.back();
  }
}
