package com.littlesword.ozbargain.categorylist;

import com.littlesword.ozbargain.network.APIInterface;
import com.littlesword.ozbargain.util.CommonUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.io.InputStream;

import rx.Observable;
import rx.functions.Func0;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by kongw1 on 17/03/16.
 */
public class BargainListPresenterTest {

    @Mock
    private CategoryListContract.View mAddNoteView;
    @Mock
    private CategoryFragment.CallBack mActivityCallback;

    @Mock
    private APIInterface apiImp;

    private BargainListPresenter mAddNotesPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mAddNotesPresenter = new BargainListPresenter(mAddNoteView, apiImp);
    }

    @Test
    public void testOpenBargain() throws Exception {

    }

    @Test
    public void testLoadBargainCategory() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("sad");
        Document document = null;
        try {
            document = Jsoup.parse(CommonUtil.readTextFile(in));
        } catch (IOException e) {
            e.printStackTrace();
        }
        final Document finalDocument = document;
        when(apiImp.getHomePageAsync(any(String.class))).thenReturn(Observable.defer(new Func0<Observable<Document>>() {
            @Override
            public Observable<Document> call() {
                return Observable.just(finalDocument);
            }
        }));
        mAddNotesPresenter.loadBargainList("");
        verify(mAddNoteView).showCategoryList(any(Document.class));
    }

    @Test
    public void testLoadBargainList() throws Exception {

        InputStream in = this.getClass().getClassLoader().getResourceAsStream("sad");
        Document document = null;
        try {
            document = Jsoup.parse(CommonUtil.readTextFile(in));
        } catch (IOException e) {
            e.printStackTrace();
        }
        final Document finalDocument = document;
        when(apiImp.getHomePageAsync(any(String.class))).thenReturn(Observable.defer(new Func0<Observable<Document>>() {
            @Override
            public Observable<Document> call() {
                return Observable.just(finalDocument);
            }
        }));
        mAddNotesPresenter.loadBargainList("");
        verify(mActivityCallback).onCategoryLoaded(any(Document.class));
    }
}