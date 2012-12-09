/*
 * Copyright 2011 vvakame <vvakame@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.vvakame.jsonpullparser.android.sample;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import net.vvakame.twitter.ResultTweet;
import net.vvakame.twitter.SearchResultGen;
import net.vvakame.util.jsonpullparser.JsonFormatException;
import net.vvakame.util.jsonpullparser.util.OnJsonObjectAddListener;
import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

public class MainActivity extends ListActivity {

	final static String PUBLIC_TIMELINE_URL = "http://search.twitter.com/search.json?q=Android";

	ArrayAdapter<String> mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1);
		setListAdapter(mAdapter);
	}

	@Override
	protected void onResume() {
		super.onResume();
		new TweetDownloadTask().execute();
	}

	class TweetDownloadTask extends AsyncTask<Void, ResultTweet, Void> {
		OnJsonObjectAddListener listener = new OnJsonObjectAddListener() {
			@Override
			public void onAdd(Object obj) {
				if (obj instanceof ResultTweet) {
					ResultTweet tweet = (ResultTweet) obj;
					publishProgress(tweet);
				}
			}
		};

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mAdapter.clear();
		}

		@Override
		protected Void doInBackground(Void... params) {

			try {
				URL url = new URL(PUBLIC_TIMELINE_URL);
				HttpURLConnection urlConnection = (HttpURLConnection) url
						.openConnection();
				try {
					SearchResultGen.get(urlConnection.getInputStream(),
							listener);
				} finally {
					urlConnection.disconnect();
				}
			} catch (JsonFormatException e) {
				Log.e("JsonPullParser", "WRYYYYYYY", e);
			} catch (IOException e) {
				Log.e("JsonPullParser", "WRYYYYYYY", e);
			}

			return null;
		}

		@Override
		protected void onProgressUpdate(ResultTweet... progress) {
			for (ResultTweet tweet : progress) {
				mAdapter.add(tweet.getText());
				mAdapter.notifyDataSetChanged();
			}
		}
	}
}