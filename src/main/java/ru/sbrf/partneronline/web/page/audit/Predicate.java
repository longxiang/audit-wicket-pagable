package ru.sbrf.partneronline.web.page.audit;

/**
 * Created by vbauer on 07/05/14.
 */
public interface Predicate<T>{
    public boolean test(T t);
}