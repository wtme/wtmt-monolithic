import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IOvertimeComment } from 'app/shared/model/overtime-comment.model';

type EntityResponseType = HttpResponse<IOvertimeComment>;
type EntityArrayResponseType = HttpResponse<IOvertimeComment[]>;

@Injectable({ providedIn: 'root' })
export class OvertimeCommentService {
  public resourceUrl = SERVER_API_URL + 'api/overtime-comments';

  constructor(protected http: HttpClient) {}

  create(overtimeComment: IOvertimeComment): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(overtimeComment);
    return this.http
      .post<IOvertimeComment>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(overtimeComment: IOvertimeComment): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(overtimeComment);
    return this.http
      .put<IOvertimeComment>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IOvertimeComment>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IOvertimeComment[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(overtimeComment: IOvertimeComment): IOvertimeComment {
    const copy: IOvertimeComment = Object.assign({}, overtimeComment, {
      createdDate:
        overtimeComment.createdDate != null && overtimeComment.createdDate.isValid() ? overtimeComment.createdDate.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdDate = res.body.createdDate != null ? moment(res.body.createdDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((overtimeComment: IOvertimeComment) => {
        overtimeComment.createdDate = overtimeComment.createdDate != null ? moment(overtimeComment.createdDate) : null;
      });
    }
    return res;
  }
}
