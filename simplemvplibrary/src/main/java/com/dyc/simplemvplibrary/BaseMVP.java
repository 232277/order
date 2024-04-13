package com.dyc.simplemvplibrary;

/**
 * func:
 * author:丁语成 on 2020/2/13 10:03
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public interface BaseMVP<M extends Model, V extends View, P extends Presenter> {
	M initM();
	V initV();
	P initP();
}
